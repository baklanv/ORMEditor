package ORM_Event;

import java.util.EventListener;

public interface ORM_EventListener extends EventListener {
    public void edgeConnected(ORM_EventObject e);
    public void processOfCreatingConstrainAssociation(ORM_EventObject e);
}
