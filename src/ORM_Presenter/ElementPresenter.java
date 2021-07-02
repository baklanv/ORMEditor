package ORM_Presenter;

import com.mxgraph.model.mxCell;
import com.sun.istack.internal.NotNull;
import org.vstu.nodelinkdiagram.DiagramElement;
import org.vstu.nodelinkdiagram.statuses.ValidateStatus;

public abstract class ElementPresenter {
    protected mxCell _mxCell;
    protected DiagramElement _diagramElement;
    protected GraphPresenter _graphPresenter;
    protected ValidateStatus _validateStatus;

    public ElementPresenter(@NotNull GraphPresenter graphPresenter) {
        _graphPresenter = graphPresenter;
    }

    protected GraphPresenter getGraphPresenter() {
        return _graphPresenter;
    }

    public DiagramElement getORM_Element() {
        return _diagramElement;
    }

    public mxCell get_mxCell() {
        return _mxCell;
    }

    void destroy() {
        getGraphPresenter().getMxGraph().removeCells(new Object[]{_mxCell}, false);
    }

    public ValidateStatus getValidateStatus() {
        return _validateStatus;
    }
}
