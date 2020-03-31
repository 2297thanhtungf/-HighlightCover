package com.dtt.hightlightcover.activity;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dtt.hightlightcover.R;
import com.dtt.hightlightcover.adapter.CategoryAdapter;
import com.dtt.hightlightcover.adapter.ItemCustomAdapter;
import com.dtt.hightlightcover.model.Category;
import com.dtt.hightlightcover.model.Content;
import com.dtt.hightlightcover.model.ItemCustom;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // data json
    private JSONArray jsonArray;

    //    data for Adapter
    private String[] titles = {"Text", "Wreaths", "Basic1", "Watercolor", "Cartoon", "Basic2", "Chalk", "Classy", "Colored", "pixel", "Girlish", "Filled", "Gradient"};
    private ArrayList<Category> listCategories;
    private ArrayList<ItemCustomAdapter> listItemCustomAdapter;
    // adapter
    private CategoryAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listCategories = new ArrayList<>();
        jsonParse();

        RecyclerView recyclerView = findViewById(R.id.rc_activitymain);

        initViews();

        adapter = new CategoryAdapter(this, listCategories);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    private void initViews() {

    }



    public String getAssetJsonData(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("home_image_config.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void jsonParse() {
        String json = getAssetJsonData(this);
//        ArrayList<ItemCustom> itemCustoms = new ArrayList<>();
        try {
            jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);

                ItemCustom itemCustom = new ItemCustom();
                try {
                    itemCustom.setId(jsonObj.getInt("id"));
                } catch (Exception e) {
                    itemCustom.setId(-1);
                }
                try {
                    itemCustom.setName(jsonObj.getString("name"));
                } catch (Exception e) {
                }
                try {
                    itemCustom.setBackgroundLock(jsonObj.getBoolean("backgroundLock"));
                } catch (Exception e) {
                }
                try {
                    itemCustom.setType(jsonObj.getInt("type"));

                } catch (Exception e) {
                }
                JSONObject contentJson = jsonObj.getJSONObject("content");
                Content content = new Content();
                try {
                    content.setBg(contentJson.getString("bg"));
                } catch (Exception e) {
                }
                try {
                    content.setFontName(contentJson.getString("fontName"));
                } catch (Exception e) {
                }
                try {
                    content.setText(contentJson.getString("text"));
                } catch (Exception e) {
                }
                try {
                    content.setTextSize(contentJson.getString("textSize"));
                } catch (Exception e) {
                }
                try {
                    content.setTextColor(contentJson.getString("textColor"));
                } catch (Exception e) {
                }
                try {
                    content.setCircleSticker(contentJson.getString("circleSticker"));
                } catch (Exception e) {
                }
                try {
                    content.setCircleColor(contentJson.getString("circleColor"));
                } catch (Exception e) {
                }
                try {
                    content.setSticker(contentJson.getString("sticker"));
                } catch (Exception e) {
                }
                try {
                    content.setBgColor(contentJson.getString("bgColor"));
                } catch (Exception e) {
                }
                try {
                    content.setBgColor(contentJson.getString("bgColor"));
                } catch (Exception e) {
                }
                try {
                    content.setCirclePercentage(contentJson.getInt("circlePercentage"));
                } catch (Exception e) {
                }
                try {
                    content.setBackgroundLock(contentJson.getBoolean("backgroundLock"));
                } catch (Exception e) {
                }
                try {
                    content.setTextMaterail(contentJson.getString("textMaterail"));
                } catch (Exception e) {
                }
                try {
                    content.setMaterailGroup(contentJson.getString("materailGroup"));
                } catch (Exception e) {
                }
                try {
                    content.setStickerColor(contentJson.getString("stickerColor"));
                } catch (Exception e) {
                }
                try {
                    content.setCircleStickerGroup(contentJson.getString("circleStickerGroup"));
                } catch (Exception e) {
                }
                try {
                    content.setStickerGroup(contentJson.getString("stickerGroup"));
                } catch (Exception e) {
                }
                try {
                    content.setStickerPercentage(contentJson.getDouble("stickerPercentage"));
                } catch (Exception e) {
                }
                try {
                    content.setStickerMaterail(contentJson.getString("stickerMaterail"));
                } catch (Exception e) {
                }
                try {
                    content.setCircleMaterail(contentJson.getString("circleMaterail"));
                } catch (Exception e) {
                }
                try {
                    content.setCircleMaterailGroup(contentJson.getString("circleMaterailGroup"));
                } catch (Exception e) {
                }


                itemCustom.setContent(content);
//                itemCustoms.add(itemCustom);
//                //todo: parse xong ItemCustom
                if (listCategories.size() == 0 || (listCategories.size() - 1) != itemCustom.getType()) {
                    String title = titles[listCategories.size()];
                    ArrayList<ItemCustom> itemCustoms = new ArrayList<>();

                    itemCustoms.add(itemCustom);
                    listCategories.add(new Category(title, itemCustoms));

                } else {
                    listCategories.get(listCategories.size() - 1).getItemCustoms().add(itemCustom);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        for (int i = 0; i < itemCustoms.size(); i++) {
//            Log.d("datdb", itemCustoms.get(i).toString());
//        }
    }


}
