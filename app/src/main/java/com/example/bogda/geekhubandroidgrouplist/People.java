package com.example.bogda.geekhubandroidgrouplist;

import android.net.Uri;

/**
 * Created by bogda on 25.10.2016.
 */

public class People implements Comparable {
    private String name;
    private Uri googleLink;
    private Uri gitHubLink;

    public People(String name, String googleLink, String gitHubLink)  {
        setName(name);
        setGitHubLink(gitHubLink);
        setGoogleLink(googleLink);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getGoogleLink() {
        return googleLink;
    }

    public void setGoogleLink(String googleLink) {
        this.googleLink = Uri.parse(googleLink);
    }

    public Uri getGitHubLink() {
        return gitHubLink;
    }

    public void setGitHubLink(String gitHubLink) {
        this.gitHubLink = Uri.parse(gitHubLink);
    }

    @Override
    public int compareTo(Object o) {
        People compPeople = (People) o;
        if(compPeople.getName().compareTo(getName())>=0)
        {
            return -1;
        }
        else{
            return 1;
        }
    }
}
