package TestUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TabUtils {


    public String name;

    @JsonProperty("default")
    public Boolean isDefault;

    @JsonProperty("public")
    public boolean isPublic;

    public String tabType;

    public String createByUserId;

    @JsonProperty("_id")
    public String tabId;


    public void setTabId(String tabId){
        this.tabId = tabId;
    }

    public String getTabId(){
        return tabId;
    }


    public Boolean getDefault ()
    {
        return isDefault;
    }

    public void setDefault (Boolean isDefault)
    {
        this.isDefault = isDefault;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }



    public Boolean getPublic ()
    {
        return isPublic;
    }



    public void setPublic (Boolean isPublic)
    {
        this.isPublic = isPublic;
    }

    public String getTabType ()
    {
        return tabType;
    }

    public void setTabType (String tabType)
    {
        this.tabType = tabType;
    }

}
