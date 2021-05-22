package ORM_Presenter;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxICell;
import com.mxgraph.util.mxDomUtils;
import org.jetbrains.annotations.NotNull;
import org.vstu.orm2diagram.model.ORM_EntityType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SubtypingPresenter extends ElementPresenter{
    public SubtypingPresenter(@NotNull GraphPresenter graphPresenter) {
        super(graphPresenter);
    }
}
