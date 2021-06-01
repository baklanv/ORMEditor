package ORM_Presenter;

import com.mxgraph.model.mxCell;
import org.jetbrains.annotations.NotNull;
import org.vstu.nodelinkdiagram.DiagramElement;

public abstract class ElementPresenter {
    protected mxCell _mxCell;
    protected DiagramElement _diagramElement;
    protected GraphPresenter _graphPresenter;

    public ElementPresenter(@NotNull GraphPresenter graphPresenter){
        _graphPresenter =  graphPresenter;
    }

    protected GraphPresenter getGraphPresenter(){
        return _graphPresenter;
    }

    void setORM_Element(@NotNull DiagramElement diagramElement){
        _diagramElement = diagramElement;
    }

    public DiagramElement getORM_Element(){
        return _diagramElement;
    }

    public mxCell get_mxCell(){
        return _mxCell;
    }

    void destroy(){
        getGraphPresenter().getMxGraph().removeCells(new Object[]{_mxCell}, true);
    }
}
