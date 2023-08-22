package com.free.hdvideodownloaderapp.videodownloader.Social_Download.free__Statics;


import com.free.hdvideodownloaderapp.videodownloader.Social_Download.Models.downloadable_resource_model;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.Models.File_type;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.Models.ResourceHolderModel;

import java.util.ArrayList;
import java.util.List;


public class free_StaticVariables {

    public static ResourceHolderModel resourse_holder;

    public static downloadable_resource_model get_by_type_position(File_type _type, int position){
        List<downloadable_resource_model> list;
        if( _type== File_type.VIDEO ){
            list= resourse_holder.getVideo_files();
        }
       else if( _type== File_type.IMAGE ){
            list= resourse_holder.getImage_files();
        }
        else if( _type== File_type.AUDIO ){
            list= resourse_holder.getAudio_files();
        }
        else
        {
            return null;
        }
        if(list !=null){
            return  list.get(position);
        }
        else
        {
            return null;
        }
    }

    public static ArrayList<downloadable_resource_model> get_downloadable_resource_model_By_Type(File_type _type){
        if( _type== File_type.VIDEO ){
            return resourse_holder.getVideo_files();
        }else if( _type== File_type.IMAGE ){
            return resourse_holder.getImage_files();
        }
        else if( _type== File_type.AUDIO ){
            return resourse_holder.getAudio_files();
        }
        else
        {
            return null;
        }
    }

}
