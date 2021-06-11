package ORM_Presenter;

import com.mxgraph.model.mxCell;
import org.jetbrains.annotations.NotNull;
import org.vstu.nodelinkdiagram.statuses.ValidateStatus;
import org.vstu.nodelinkdiagram.util.Point;
import org.vstu.orm2diagram.model.ORM_UnaryPredicate;

public class UnaryPredicatePresenter extends ElementPresenter {
    private String _style = "verticalLabelPosition=top;resizable=false;movable=false;";
    protected RolePresenter _role;

    public UnaryPredicatePresenter(@NotNull GraphPresenter graphPresenter, @NotNull Point pos, @NotNull ORM_UnaryPredicate orm_unaryPredicate) {
        super(graphPresenter);

        _mxCell = (mxCell) graphPresenter.getMxGraph().insertVertex(graphPresenter.getMxGraph().getDefaultParent(),
                null, "", pos.getX(), pos.getY(), 40, 30,
                "verticalLabelPosition=top;resizable=false;foldable=false;opacity=1;editable=false;");

        //создание роли
        mxCell mxCellRole = (mxCell) graphPresenter.getMxGraph().insertVertex(_mxCell, null, "", 5, 5, 30, 20, _style);
        _role = new RolePresenter(graphPresenter, mxCellRole, orm_unaryPredicate.getItem(0));


        _diagramElement = orm_unaryPredicate;
        ((ORM_UnaryPredicate) _diagramElement).setPosition(pos);
        _validateStatus = ValidateStatus.Acceptable;
    }

    // -------------- Характеристики представления для Value Type -----------
    public RolePresenter getRole() {
        return _role;
    }

    public void fail() {
        _graphPresenter.getMxGraph().setCellStyle(_style + "strokeColor=red", new Object[]{_role.get_mxCell()});
        _validateStatus = ValidateStatus.Invalid;
    }

    public void success() {
        _graphPresenter.getMxGraph().setCellStyle(_style, new Object[]{_role.get_mxCell()});
        _validateStatus = ValidateStatus.Acceptable;
    }
}
