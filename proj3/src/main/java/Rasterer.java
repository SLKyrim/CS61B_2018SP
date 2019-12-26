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

    private double rootLonDist = rootLRLon - rootULLon;
    private double rootLatDist = rootULLat - rootLRLat;

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

        Map<String, Object> results = new HashMap<>();
        if (!checkQuery(requestULLon, requestLRLon, requestULLat, requestLRLat)) {
            results.put("render_grid", new String[1][1]);
            results.put("raster_ul_lon", 0.0);
            results.put("raster_ul_lat", 0.0);
            results.put("raster_lr_lon", 0.0);
            results.put("raster_lr_lat", 0.0);
            results.put("depth", 1);
            results.put("query_success", false);
            return results;
        }

        // longitudinal distance per pixel in query box
        double requestLonDPP = (requestLRLon - requestULLon) / requestWidth;
        // depth of image
        int depth = calcDepth(requestLonDPP);

        requestULLon = requestULLon < rootULLon ? rootULLon : requestULLon;
        requestLRLon = requestLRLon > rootLRLon ? rootLRLon : requestLRLon;
        requestULLat = requestULLat > rootULLat ? rootULLat : requestULLat;
        requestLRLat = requestLRLat < rootLRLat ? rootLRLat : requestLRLat;

        double eachLonDist = rootLonDist / Math.pow(2, depth);
        double eachLatDist = rootLatDist / Math.pow(2, depth);
        int startX = (int) Math.floor((requestULLon - rootULLon) / eachLonDist);
        int startY = (int) Math.floor((rootULLat - requestULLat) / eachLatDist);
        double lonDist = requestLRLon - (eachLonDist * (startX + 1) + rootULLon);
        double latDist = (rootULLat - eachLatDist * (startY + 1)) - requestLRLat;
        int endX = (int) Math.ceil(lonDist / eachLonDist) + startX;
        int endY = (int) Math.ceil(latDist / eachLatDist) + startY;
        String[][] render_grid = generateGrid(startX, startY, endX, endY, depth);

        results.put("render_grid", render_grid);
        results.put("raster_ul_lon", requestULLon);
        results.put("raster_ul_lat", requestULLat);
        results.put("raster_lr_lon", requestLRLon);
        results.put("raster_lr_lat", requestLRLat);
        results.put("depth", depth);
        results.put("query_success", true);

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

    private String[][] generateGrid(int sX, int sY, int eX, int eY, int d) {
        int xLength = eX - sX + 1;
        int yLength = eY - sY + 1;
        String[][] grid = new String[yLength][xLength];

        for (int row = 0; row < yLength; row++) {
            for (int col = 0; col < xLength; col++) {
                int x = sX + col;
                int y = sY + row;
                String img = "d" + d + "_x" + x + "_y" + y + ".png";
                grid[row][col] = img;
            }
        }
        return grid;
    }
}
