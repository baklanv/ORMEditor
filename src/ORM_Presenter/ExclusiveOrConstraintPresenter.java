package ORM_Presenter;

import com.mxgraph.model.mxCell;
import org.jetbrains.annotations.NotNull;
import org.vstu.nodelinkdiagram.statuses.ValidateStatus;
import org.vstu.nodelinkdiagram.util.Point;
import org.vstu.orm2diagram.model.ORM_ExclusionOrConstraint;

public class ExclusiveOrConstraintPresenter extends ElementPresenter {
    String _style = "shape=ellipse;perimeter=ellipsePerimeter;spacing=10;strokeColor=purple;foldable=false;editable=0;resizable=false;";

    public ExclusiveOrConstraintPresenter(@NotNull GraphPresenter graphPresenter, @NotNull Point pos, @NotNull ORM_ExclusionOrConstraint orm_exclusionOrConstraint) {
        super(graphPresenter);

        _mxCell = (mxCell) graphPresenter.getMxGraph().insertVertex(graphPresenter.getMxGraph().getDefaultParent(),
                null, "", pos.getX(), pos.getY(), 30, 30, _style);

        mxCell mxCell = (mxCell) graphPresenter.getMxGraph().insertVertex(_mxCell, null, "", 10, 10, 10, 10,
                "shape=ellipse;spacing=10;resizable=false;strokeColor=purple;fillColor=purple;" +
                        "editable=0;movable=false;connectable=false;");
        mxCell.setConnectable(false);
        mxCell line1 = (mxCell) graphPresenter.getMxGraph().insertVertex(_mxCell, null, "", 0, 10, 30, 10,
                "shape=line;perimeter=linePerimeter;spacing=10;resizable=false;strokeColor=purple;fillColor=purple;" +
                        "editable=0;movable=false;connectable=false;rotation=45");
        line1.setConnectable(false);
        mxCell line2 = (mxCell) graphPresenter.getMxGraph().insertVertex(_mxCell, null, "", 0, 10, 30, 10,
                "shape=line;perimeter=linePerimeter;spacing=10;resizable=false;strokeColor=purple;fillColor=purple;" +
                        "editable=0;movable=false;connectable=false;rotation=135");
        line2.setConnectable(false);

        _diagramElement = orm_exclusionOrConstraint;
        _validateStatus = ValidateStatus.Acceptable;
    }

    public void fail() {
        _graphPresenter.getMxGraph().setCellStyle(_style + "strokeColor=red", new Object[]{_mxCell});
        _validateStatus = ValidateStatus.Invalid;
    }

    public void success() {
        _graphPresenter.getMxGraph().setCellStyle(_style, new Object[]{_mxCell});
        _validateStatus = ValidateStatus.Acceptable;
    }
}
