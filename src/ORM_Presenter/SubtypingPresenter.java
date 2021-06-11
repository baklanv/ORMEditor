package ORM_Presenter;

import com.mxgraph.model.mxCell;
import org.jetbrains.annotations.NotNull;
import org.vstu.orm2diagram.model.ORM_Subtyping;

public class SubtypingPresenter extends ElementPresenter{
    public SubtypingPresenter(@NotNull GraphPresenter graphPresenter, @NotNull mxCell edge, @NotNull ORM_Subtyping subtyping) {
        super(graphPresenter);

        _mxCell = edge;
        _mxCell.setStyle("strokeWidth=2;endArrow=classic;strokeColor=purple;");
        _diagramElement = subtyping;
    }
}
