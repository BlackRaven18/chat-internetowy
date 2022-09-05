import com.client.client.Main;
import com.client.client.ManageViews;
import org.junit.Assert;
import org.junit.Test;

public class ManageViewsTests {

    @Test
    public void sceneShouldBeChanged(){
        Main.main(null);

        Assert.assertTrue(ManageViews.changeView("add-delete-modify-user-view.fxml"));
    }

    @Test
    public void sceneAndDimesionsShouldBeChanged(){
        Main.main(null);
        Assert.assertTrue(ManageViews.changeView("add-delete-modify-user-view.fxml", 200, 200));
    }


}
