package com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model;

import java.util.ArrayList;

public class NewVoiceCacheListModel {

    ArrayList<VoiceChatModel> playerproVoiceChatItems;

    String id;
    String string;

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public String getString() {
        return string;
    }

    public NewVoiceCacheListModel(ArrayList<VoiceChatModel> playerproVoiceChatItems, String string, String id) {
        this.playerproVoiceChatItems = playerproVoiceChatItems;
        this.string = string;
        this.id = id;
    }

    public void setString(String string) {
        this.string = string;
    }

    public ArrayList<VoiceChatModel> getVoiceChatItems() {
        return playerproVoiceChatItems;
    }

    public void setVoiceChatItems(ArrayList<VoiceChatModel> playerproVoiceChatItems) {
        this.playerproVoiceChatItems = playerproVoiceChatItems;
    }

}
