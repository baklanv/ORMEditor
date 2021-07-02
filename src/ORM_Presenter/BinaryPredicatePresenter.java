package ORM_Presenter;

import com.mxgraph.model.mxCell;
import com.sun.istack.internal.NotNull;
import org.vstu.nodelinkdiagram.statuses.ValidateStatus;
import org.vstu.nodelinkdiagram.util.Point;
import org.vstu.orm2diagram.model.ORM_BinaryPredicate;

public class BinaryPredicatePresenter extends ElementPresenter {
    private String _style = "deletable=false;verticalLabelPosition=top;movable=false;resizable=false;";
    protected RolePresenter _role;
    protected RolePresenter _inverseRole;
    protected mxCell _mxCellParent;

    public BinaryPredicatePresenter(@NotNull GraphPresenter graphPresenter, @NotNull Point pos, @NotNull ORM_BinaryPredicate orm_binaryPredicate) {
        super(graphPresenter);

        // _mxCell - _mxCellGrandParent
        _mxCell = (mxCell) graphPresenter.getMxGraph().insertVertex(graphPresenter.getMxGraph().getDefaultParent(), null, "",
                pos.getX(), pos.getY(), 70, 30, "opacity=0;connectable=false;foldable=false;verticalLabelPosition=top;resizable=false;editable=0;");
        _mxCell.setConnectable(false);

        _mxCellParent = (mxCell) graphPresenter.getMxGraph().insertVertex(_mxCell, null, "", 5, 5, 60, 20,
                "opacity=0;connectable=false;verticalLabelPosition=top;deletable=false;foldable=false;resizable=false;movable=false;editable=0;");
        _mxCellParent.setConnectable(false);

        // создание левой роли
        mxCell mxCellRole = (mxCell) graphPresenter.getMxGraph().insertVertex(_mxCellParent, null, "", 0, 0, 30, 20,
                _style);

        // создаем левый RolePresenter
        _role = new RolePresenter(graphPresenter, mxCellRole, orm_binaryPredicate.getItem(0));

        //создание правой роли
        mxCell mxCellInverseRole = (mxCell) graphPresenter.getMxGraph().insertVertex(_mxCellParent, null, "", 30, 0, 30, 20,
                _style);

        // создаем правый RolePresenter
        _inverseRole = new RolePresenter(graphPresenter, mxCellInverseRole, orm_binaryPredicate.getItem(1));

        _diagramElement = orm_binaryPredicate;
        ((ORM_BinaryPredicate) _diagramElement).setPosition(pos);
        _validateStatus = ValidateStatus.Acceptable;
    }

    public RolePresenter getRole() {
        return _role;
    }

    public RolePresenter getInverseRole() {
        return _inverseRole;
    }

    public void fail() {
        _graphPresenter.getMxGraph().setCellStyle(_style + "strokeColor=red", new Object[]{_role.get_mxCell(), _inverseRole.get_mxCell()});
        _validateStatus = ValidateStatus.Invalid;
    }

    public void success() {
        _graphPresenter.getMxGraph().setCellStyle(_style, new Object[]{_role.get_mxCell(), _inverseRole.get_mxCell()});
        _validateStatus = ValidateStatus.Acceptable;
    }
}
