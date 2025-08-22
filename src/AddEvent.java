import java.util.EventObject;

public class AddEvent extends EventObject {

    private Password password;
    public AddEvent(Object source) {
        super(source);
    }

    public AddEvent(Object source, Password password) {
        super(source);

        this.password = password;

    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }
}
