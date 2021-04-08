package net.bungie.telegram.commands.impl;

import lombok.extern.slf4j.Slf4j;
import net.bungie.telegram.commands.BotCommandNoArgument;
import net.bungie.utils.BungieApi;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Command return info about Xur vendor and weekend sale list
 */
@Slf4j
public class Xur extends BotCommandNoArgument {
    private final AtomicBoolean tradeTime = new AtomicBoolean(true);
    private final CopyOnWriteArrayList<String> xurTradeList = new CopyOnWriteArrayList<>();

    private LocalDateTime startTradeTime;
    private LocalDateTime endTradeTime;
    private String xurLocation = null;

    public Xur(String commandId, String description) {
        super(commandId, description);
        insertTradeTimesIfAbsent();
    }

    /*
        Vendors: 400
        When obtaining vendor information, this will return summary information about the Vendor or Vendors being returned.
        VendorCategories: 401
        When obtaining vendor information, this will return information about the categories of items provided by the Vendor.
        VendorSales: 402
        When obtaining vendor information, this will return the information about items being sold by the Vendor.
    */
    @Override
    public String execute() {
        LocalDateTime localDateTime = LocalDateTime.now();

        if (!localDateTime.isAfter(startTradeTime) && !localDateTime.isBefore(endTradeTime)) {
            if (tradeTime.get()) {
                insertTradeTimesIfAbsent();
                xurLocation = null;
                tradeTime.set(false);
            }
            return "He only appears on the weekends between 12 PM EST on Friday to 12 PM EST on Tuesday";
        }

        if (xurLocation == null) {
            insertXurLocation();
        }

        if (xurTradeList.isEmpty()) {
            insertXurTradeList();
        }

        return xurTradeList.toString();
    }

    private synchronized void insertXurTradeList() {
        List<Integer> saleItems = new ArrayList<>();
        String getSaleList = BungieApi.request("vendors/?components=400,401,402");

        for (int i = 1; i < 5; i++) {
            Integer readValue = new JSONObject(getSaleList).getJSONObject("Response").getJSONObject("categories")
                    .getJSONObject("data").getJSONObject("2190858386").getJSONArray("categories").getJSONObject(0)
                    .getJSONArray("itemIndexes").optInt(i);
            saleItems.add(readValue);
        }

        String xurManifest = BungieApi.request("Manifest/DestinyVendorDefinition/2190858386/");
        JSONArray jsonXurManifest = new JSONObject(xurManifest).getJSONObject("Response").getJSONArray("itemList");

        for (Integer itemIndex : saleItems) {
            String itemHash = jsonXurManifest.getJSONObject(itemIndex).optString("itemHash");

            String itemInfo = BungieApi.request("Manifest/DestinyInventoryItemDefinition/" + itemHash + "/");
            String itemName = new JSONObject(itemInfo).getJSONObject("Response")
                    .getJSONObject("displayProperties").getString("name");

            xurTradeList.add(itemName);
        }

        System.out.println(xurTradeList);
    }

    private synchronized void insertXurLocation() {
        try {
            Document doc = Jsoup.connect("https://wherethefuckisxur.com/").get();
            String text = doc.getElementsByClass("page-title box-title").text();

            xurLocation = switch (text.toLowerCase().split("\\s+")[0]) {
                case "tower" -> "Tower";
                case "edz" -> "EDZ";
                case "nessus" -> "Nessus";
                default -> null;
            };

        } catch (IOException e) {
            log.error("Error, while access to Xur location: ", e);
        }
    }

    private synchronized void insertTradeTimesIfAbsent() {
        LocalDate localDate = LocalDate.now();
        LocalDate nextFriday = localDate.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
        LocalDate nextTuesday = localDate.with(TemporalAdjusters.next(DayOfWeek.TUESDAY));
        startTradeTime = LocalDateTime.of(nextFriday, LocalTime.of(20, 0));
        endTradeTime = LocalDateTime.of(nextTuesday, LocalTime.of(20, 0));
    }

    //TODO DELETE ON RELEASE
    private String testJsonDataSaleList() {
        return "{\"Response\":{\"vendorGroups\":{\"data\":{\"groups\":[{\"vendorGroupHash\":3227191227,\"vendorHashes\":[2190858386]}]},\"privacy\":1},\"vendors\":{\"data\":{\"2190858386\":{\"vendorHash\":2190858386,\"nextRefreshDate\":\"2021-04-02T09:00:00Z\",\"enabled\":true}},\"privacy\":1},\"categories\":{\"data\":{\"2190858386\":{\"categories\":[{\"displayCategoryIndex\":0,\"itemIndexes\":[0,3,47,69,96]},{\"displayCategoryIndex\":4,\"itemIndexes\":[247]}]}},\"privacy\":1},\"sales\":{\"data\":{\"2190858386\":{\"saleItems\":{\"0\":{\"vendorItemIndex\":0,\"itemHash\":3875551374,\"quantity\":1,\"costs\":[{\"itemHash\":1022552290,\"quantity\":97}]},\"3\":{\"vendorItemIndex\":3,\"itemHash\":1345867570,\"quantity\":1,\"costs\":[{\"itemHash\":1022552290,\"quantity\":29}]},\"47\":{\"vendorItemIndex\":47,\"itemHash\":903984858,\"quantity\":1,\"costs\":[{\"itemHash\":1022552290,\"quantity\":23}]},\"69\":{\"vendorItemIndex\":69,\"itemHash\":2578771006,\"quantity\":1,\"costs\":[{\"itemHash\":1022552290,\"quantity\":23}]},\"96\":{\"vendorItemIndex\":96,\"itemHash\":4057299718,\"quantity\":1,\"costs\":[{\"itemHash\":1022552290,\"quantity\":23}]},\"247\":{\"vendorItemIndex\":247,\"itemHash\":2125848607,\"quantity\":1,\"costs\":[]}}}},\"privacy\":1}},\"ErrorCode\":1,\"ThrottleSeconds\":0,\"ErrorStatus\":\"Success\",\"Message\":\"Ok\",\"MessageData\":{}}";
    }

}
