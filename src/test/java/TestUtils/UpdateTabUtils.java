package TestUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateTabUtils{

    @JsonProperty("default")
    private boolean isDefault;

    private String _id;

    private String createByUserId;

    private String name;

    @JsonProperty("public")
    private boolean isPublic;

    private String tabType;

    public Boolean getDefault ()
    {
        return isDefault;
    }

    public void setDefault (Boolean isDefault)
    {
        this.isDefault = isDefault;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getCreateByUserId ()
    {
        return createByUserId;
    }

    public void setCreateByUserId (String createByUserId)
    {
        this.createByUserId = createByUserId;
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