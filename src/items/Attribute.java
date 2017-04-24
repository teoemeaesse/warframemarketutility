package items;

/**
 * Created by tomas on 4/23/2017.
 */
public class Attribute {
    private String identifier, value;

    public Attribute(String identifier, String value) {
        this.identifier = identifier;
        this.value = value;
    }

    public String getIdentifier() {
        return identifier;
    }
    public String getValue() {
        return value;
    }
}
