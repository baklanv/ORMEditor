package ORM_Presenter;

import com.mxgraph.model.mxCell;
import com.sun.istack.internal.NotNull;
import org.vstu.nodelinkdiagram.statuses.ValidateStatus;
import org.vstu.orm2diagram.model.ORM_Role;

public class RolePresenter extends ElementPresenter{
    public RolePresenter(@NotNull GraphPresenter graphPresenter, @NotNull mxCell mxCell, @NotNull ORM_Role orm_role) {
        super(graphPresenter);

        _mxCell = mxCell;

        _diagramElement = orm_role;
        ((ORM_Role)_diagramElement).setName(_mxCell.getValue().toString());
        _validateStatus = ValidateStatus.Acceptable;
    }

    // -------------- Характеристики представления для Role -----------
    public void setName(String name) {
        _mxCell.setValue(name);
        ((ORM_Role) _diagramElement).setName(name);
    }

    public String getName() {
        return (String) _mxCell.getValue();
    }
}
