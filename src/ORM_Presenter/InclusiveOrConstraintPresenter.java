package ORM_Presenter;

import com.mxgraph.model.mxCell;
import org.jetbrains.annotations.NotNull;
import org.vstu.nodelinkdiagram.util.Point;


public class InclusiveOrConstraintPresenter extends ElementPresenter {
    public InclusiveOrConstraintPresenter(@NotNull GraphPresenter graphPresenter, @NotNull Point pos) {
        super(graphPresenter);

        _mxCell = (mxCell) graphPresenter.getMxGraph().insertVertex(graphPresenter.getMxGraph().getDefaultParent(),
                null, "", pos.getX(), pos.getY(), 26, 26,
                "shape=ellipse;perimeter=ellipsePerimeter;spacing=10;" +
                        "strokeColor=purple;foldable=false;" +
                        "editable=0;resizable=false");
        //_mxCell.setConnectable(true);
        mxCell _mxCell1 = (mxCell) graphPresenter.getMxGraph().insertVertex(_mxCell,
                null, "", 8, 8, 10, 10,
                "shape=ellipse;perimeter=ellipsePerimeter;spacing=10;" +
                        "resizable=false;strokeColor=purple;fillColor=purple;" +
                        "editable=0;movable=false;connectable=false;");
        _mxCell1.setConnectable(false);


    }
}