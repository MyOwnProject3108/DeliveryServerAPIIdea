package ResponsePayload;

/**
 * Created by Faiyyaz.Shaik on 10/23/2017.
 */
public class Markets {

    private String sid;

    private String _id;

    private String name;

    private String dfrStorageId;

    public String getSid ()
    {
        return sid;
    }

    public void setSid (String sid)
    {
        this.sid = sid;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getDfrStorageId ()
    {
        return dfrStorageId;
    }

    public void setDfrStorageId (String dfrStorageId)
    {
        this.dfrStorageId = dfrStorageId;
    }

}
