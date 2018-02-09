package byteista.sahayam.MainActivity;

/**
 * Created by Lykan on 10-02-2018.
 */

public class EventsModel {
    public EventsModel(String NAME, String DATE, String DISCRIPTION, int LIKES) {
        this.NAME = NAME;
        this.DATE = DATE;
        this.DISCRIPTION = DISCRIPTION;
        this.LIKES = LIKES;
        this.LIKED =false;
    }

    public String NAME;
public String DATE;
public String DISCRIPTION;
public int LIKES;
public boolean LIKED;
}
