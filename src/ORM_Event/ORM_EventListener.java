package ORM_Event;

import java.util.EventListener;

public interface ORM_EventListener extends EventListener {
    void edgeConnected(ORM_EventObject e);

    void processOfCreatingConstrainAssociation(ORM_EventObject e);
}
