package viewframe;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ViewerAction extends AbstractAction {
    private String actionName;
    private Action action;
    private ViewerFrame frame;

    public ViewerAction(ImageIcon imageIcon, String s, ViewerFrame viewerFrame) {
        this.actionName = s;
        this.frame = viewerFrame;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        ViewerService service = ViewerService.getInstance();
        Action action = getAction(this.actionName);
        action.execute(service, frame);
    }

    public Action getAction(String actionName) {
        try {
            if (this.action == null) {
                this.action = (Action) Class.forName(actionName).newInstance();
            }
            return this.action;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
