package ORM_Event;

import com.mxgraph.model.mxCell;

import java.util.EventObject;

public class ORM_EventObject extends EventObject {

    private mxCell _edge;

    public void set_mxCell(mxCell edge){
        _edge = edge;
    }

    public mxCell get_mxCell(){
        return _edge;
    }

    public ORM_EventObject(Object source) {
        super(source);
    }

}
