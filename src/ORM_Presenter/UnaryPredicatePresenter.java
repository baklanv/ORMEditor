package ORM_Presenter;

import com.mxgraph.model.mxCell;
import org.jetbrains.annotations.NotNull;
import org.vstu.nodelinkdiagram.util.Point;

public class UnaryPredicatePresenter extends ElementPresenter{
    public UnaryPredicatePresenter(@NotNull GraphPresenter graphPresenter, @NotNull Point pos) {
        super(graphPresenter);

        _mxCell = (mxCell) graphPresenter.getMxGraph().insertVertex(graphPresenter.getMxGraph().getDefaultParent(),
                null, "", pos.getX(), pos.getY(), 30, 20,
                "verticalLabelPosition=top;resizable=false");
    }
}
