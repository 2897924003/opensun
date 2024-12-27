package article.inbound.co;

public abstract class CommandObject {
    public long actorId;

    public CommandObject() {
    }

    public abstract Long verifyCOSecurity();


}
