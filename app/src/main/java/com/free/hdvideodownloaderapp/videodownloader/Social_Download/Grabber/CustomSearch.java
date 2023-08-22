package com.free.hdvideodownloaderapp.videodownloader.Social_Download.Grabber;

import android.content.Context;
import android.util.JsonReader;
import android.util.JsonToken;

import com.free.hdvideodownloaderapp.videodownloader.Social_Download.Models.CustomGrabberModel;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.Models.File_type;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.free_Utils.free_UUtils;
import com.free.hdvideodownloaderapp.videodownloader.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;


public class CustomSearch {

        public static List<CustomGrabberModel> Search(Context mContext, String Url, String html)
        {
            List<CustomGrabberModel> CustomGrabberModelList=new ArrayList<>();
            CustomGrabberModel customGrabberModel=new CustomGrabberModel();
            String[] customised_searches_filters = mContext.getResources().getStringArray(R.array.customised_searches);
            JsonReader reader = new JsonReader(new StringReader(html));
            reader.setLenient(true);
            boolean IsVideoFound=false;
            try {
                if(reader.peek() == JsonToken.STRING) {
                    String domStr = reader.nextString();
                    if(domStr != null) {
                        Document document= Jsoup.parse(domStr);
                        if(Url.contains(customised_searches_filters[0]))
                        {
                            Elements AllPaylods= document.select("link[rel=\"preload\"]");
                            for (Element el :AllPaylods) {
                                if(el.attr("href").endsWith(".mp4.m3u8"))
                                {
                                    IsVideoFound=true;
                                    customGrabberModel .setVideoUrl(el.attr("href"));
                                    customGrabberModel.set_file_type(File_type.VIDEO);
                                    customGrabberModel.setM3u8(true);
                                    CustomGrabberModelList.add(customGrabberModel);
                                }
                            }
                            if(IsVideoFound==false)
                            {
                                for (Element el :AllPaylods) {
                                    if(el.attr("href").contains(".m3u8"))
                                    {
                                        IsVideoFound=true;
                                        customGrabberModel .setVideoUrl(el.attr("href"));
                                        customGrabberModel.set_file_type(File_type.VIDEO);
                                        customGrabberModel.setM3u8(true);
                                        CustomGrabberModelList.add(customGrabberModel);
                                    }
                                }
                            }
                        }
                        if(Url.contains(customised_searches_filters[1]))
                        {
                            JSONArray qualityItems_jsonArray;

                            Elements el=document.select("script");
                            for (Element e : el)
                            {
                                String _eHtml=e.html().trim();
                                if(_eHtml.startsWith("var flashvars_"))
                                {
                                    String SS=_eHtml;
                                    String SSS=SS;

                                   String strJsonObject= free_UUtils.removeBackSlash(_eHtml.split("qualityItems")[1].split(" = ")[1].split("playerObjList")[0]).substring(1);
                                    strJsonObject= strJsonObject.trim().substring(0,strJsonObject.trim().length() - 2);
                                    qualityItems_jsonArray = new JSONArray(strJsonObject);

                                    for(int i = 0; i<qualityItems_jsonArray.length(); i++)
                                    {
                                        JSONObject videoUrl_jsonObject = qualityItems_jsonArray.getJSONObject(i);
                                        customGrabberModel=new CustomGrabberModel();
                                        customGrabberModel .setVideoUrl(videoUrl_jsonObject.getString("url"));
                                        customGrabberModel.set_file_type(File_type.VIDEO);
                                        customGrabberModel.setM3u8(false);
                                        CustomGrabberModelList.add(customGrabberModel);
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return CustomGrabberModelList;
        }
}
