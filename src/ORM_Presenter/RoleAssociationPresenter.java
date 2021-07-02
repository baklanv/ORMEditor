package ORM_Presenter;

import com.mxgraph.model.mxCell;
import com.sun.istack.internal.NotNull;
import org.vstu.orm2diagram.model.ORM_RoleAssociation;

public class RoleAssociationPresenter extends ElementPresenter {
    public RoleAssociationPresenter(@NotNull GraphPresenter graphPresenter, @NotNull mxCell edge, @NotNull ORM_RoleAssociation roleAssociation) {
        super(graphPresenter);

        _mxCell = edge;
        _mxCell.setStyle("strokeWidth=1.5;endArrow=\"\";strokeColor=black;");

        _diagramElement = roleAssociation;
    }
}
