package ORM_Presenter;

import com.mxgraph.model.mxCell;
import org.jetbrains.annotations.NotNull;
import org.vstu.nodelinkdiagram.util.Point;

public class BinaryPredicatePresenter extends ElementPresenter{
    mxCell _mxCellRole;
    mxCell _mxCellInverseRole;
    mxCell _mxCellParent;

    public BinaryPredicatePresenter(@NotNull GraphPresenter graphPresenter,  @NotNull Point pos) {
        super(graphPresenter);

        // _mxCell - _mxCellGrandParent
        _mxCell = (mxCell) graphPresenter.getMxGraph().insertVertex(graphPresenter.getMxGraph().getDefaultParent(),
                null, "",  pos.getX(), pos.getY(), 66, 26,
                "opacity=0;connectable=false;foldable=false;verticalLabelPosition=top;resizable=false;");
        _mxCell.setConnectable(false);

        _mxCellParent = (mxCell) graphPresenter.getMxGraph().insertVertex(_mxCell, null, "", 3, 3, 60, 20, "opacity=0;connectable=false;verticalLabelPosition=top;deletable=false;foldable=false;resizable=false;movable=false");
        _mxCellParent.setConnectable(false);

        _mxCellRole = (mxCell) graphPresenter.getMxGraph().insertVertex(_mxCellParent, null, "", 0, 0, 30, 20, "editable=0;deletable=false;verticalLabelPosition=top;movable=false;resizable=false");

        _mxCellInverseRole = (mxCell) graphPresenter.getMxGraph().insertVertex(_mxCellParent, null, "", 30, 0, 30, 20, "editable=0;deletable=false;verticalLabelPosition=top;movable=false;resizable=false");

    }
}
