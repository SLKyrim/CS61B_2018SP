import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    private double tileSize = MapServer.TILE_SIZE;

    // Border of the entire map
    private double rootULLat = MapServer.ROOT_ULLAT;
    private double rootULLon = MapServer.ROOT_ULLON;
    private double rootLRLat = MapServer.ROOT_LRLAT;
    private double rootLRLon = MapServer.ROOT_LRLON;

    public Rasterer() {
        // YOUR CODE HERE
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        System.out.println(params);

        double requestLRLon = params.get("lrlon");
        double requestULLon = params.get("ullon");
        double requestWidth = params.get("w");
        double requestHeight = params.get("h");
        double requestULLat = params.get("ullat");
        double requestLRLat = params.get("lrlat");
        boolean query_success = true;

        // query success
        Map<String, Object> results = new HashMap<>();
        if (!checkQuery(requestULLon, requestLRLon, requestULLat, requestLRLat)) {
            query_success = false;
        }
        // Longitudinal distance per pixel in query box
        double requestLonDPP = (requestLRLon - requestULLon) / requestWidth;
        // Depth of image
        int depth = calcDepth(requestLonDPP);
        // Raster parameters
        int rasterULLonNum = calcRasterParamNum(depth, requestULLon, rootULLon, rootLRLon);
        double rasterULLon = calcRasterParam(depth, rasterULLonNum, rootULLon, rootLRLon, true, true);
        int rasterULLatNum = calcRasterParamNum(depth, requestULLat, rootULLat, rootLRLat);
        double rasterULLat = calcRasterParam(depth, rasterULLatNum, rootULLat, rootLRLat, true, false);
        int rasterLRLonNum = calcRasterParamNum(depth, requestLRLon, rootULLon, rootLRLon);
        double rasterLRLon = calcRasterParam(depth, rasterLRLonNum, rootULLon, rootLRLon, false, true);
        int rasterLRLatNum = calcRasterParamNum(depth, requestLRLat, rootULLat, rootLRLat);
        double rasterLRLat = calcRasterParam(depth, rasterLRLatNum, rootULLat, rootLRLat, false, false);
        // Raster grid
        int rowNum = rasterLRLatNum - rasterULLatNum + 1;
        int colNum = rasterLRLonNum - rasterULLonNum + 1;
        String[][] render_grid = new String[rowNum][colNum];
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                render_grid[i][j] = "d" + depth + "_x" + (j + rasterULLonNum) + "_y" + (i + rasterULLatNum) + ".png";
            }
        }

        results.put("render_grid", render_grid);
        results.put("raster_ul_lon", rasterULLon);
        results.put("raster_ul_lat", rasterULLat);
        results.put("raster_lr_lon", rasterLRLon);
        results.put("raster_lr_lat", rasterLRLat);
        results.put("depth", depth);
        results.put("query_success", query_success);

        return results;
    }

    private boolean checkQuery(double ULLon, double LRLon, double ULLat, double LRLat) {
        boolean isLonLegal = (ULLon >= rootULLon || LRLon <= rootLRLon) && LRLon > ULLon;
        boolean isLatLegal = (ULLat <= rootULLat || LRLat >= rootLRLat) && LRLat < ULLat;
        return isLonLegal && isLatLegal;
    }

    /** calculate the depth of image based on the lonDPP of query box
     * @source https://github.com/zangsy/cs61b_sp19
     * @param requestLonDPP
     * @return
     */
    private int calcDepth(double requestLonDPP) {
        double baseLonDPP = (rootLRLon - rootULLon) / tileSize;
        int depth = (int) Math.ceil(Math.log(requestLonDPP / baseLonDPP) / Math.log(0.5));
        if (depth < 7) {
            return depth;
        }
        return 7;
    }

    /** Calculate the corresponding grid number of the raster parameter
     * @source https://github.com/zangsy/cs61b_sp19
     * @param depth
     * @param requestParam
     * @param rootUL
     * @param rootLR
     * @return
     */
    private int calcRasterParamNum(int depth, double requestParam, double rootUL, double rootLR) {
        int bound = (int) (Math.pow(2, depth) - 1);
        double currTileSize = Math.abs(rootUL - rootLR) / (bound + 1);
        double tmp = Math.abs(requestParam - rootUL) / currTileSize;
        int rasterParamNum = (int) Math.floor(tmp);

        if (rasterParamNum > bound) {
            rasterParamNum = bound;
        } else if (rasterParamNum < 0) {
            rasterParamNum = 0;
        }

        return rasterParamNum;
    }

    /** Calculate the raster parameter
     * @source https://github.com/zangsy/cs61b_sp19
     * @param depth
     * @param rasterParamNum
     * @param rootUL
     * @param rootLR
     * @param isUL
     * @param isLon
     * @return
     */
    private double calcRasterParam(int depth, int rasterParamNum, double rootUL, double rootLR, boolean isUL, boolean isLon) {
        int bound = (int) (Math.pow(2, depth) - 1);
        double currTileSize = Math.abs(rootUL - rootLR) / (bound + 1);
        double rasterParam;

        if (isUL) {
            if (isLon) {
                rasterParam = rootUL + rasterParamNum * currTileSize;
            } else {
                rasterParam = rootUL - rasterParamNum * currTileSize;
            }
        } else {
            if (isLon) {
                rasterParam = rootUL + (rasterParamNum + 1) * currTileSize;
            } else {
                rasterParam = rootUL - (rasterParamNum + 1) * currTileSize;
            }
        }

        return rasterParam;
    }

}
