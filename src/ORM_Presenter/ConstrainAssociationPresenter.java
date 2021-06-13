package ORM_Presenter;

import com.mxgraph.model.mxCell;
import org.jetbrains.annotations.NotNull;
import org.vstu.orm2diagram.model.ORM_ConstraintAssociation;

public class ConstrainAssociationPresenter extends ElementPresenter {
    public ConstrainAssociationPresenter(@NotNull GraphPresenter graphPresenter, @NotNull mxCell m1, @NotNull mxCell m2, @NotNull ORM_ConstraintAssociation constraintAssociation) {
        super(graphPresenter);

        _mxCell = (mxCell) graphPresenter.getMxGraph().insertEdge(graphPresenter.getMxGraph().getDefaultParent(),
                null, "", m1, m2,
                "strokeWidth=2;dashed=1;strokeColor=purple;strokeWidth=2;");

        _diagramElement = constraintAssociation;
    }
}
