import com.client.client.Main;
import com.client.client.TextFieldFormatConfigurator;
import javafx.scene.control.TextField;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TextFieldFormatConfiguratorTests {

    @Before
    public void setUp(){
        Main.main(null);
    }

    @Test
    public void textFieldShouldBeConfigurateToIntFormatOnly(){
        TextField testField = new TextField();
        TextFieldFormatConfigurator configurator = new TextFieldFormatConfigurator();
        Assert.assertTrue(configurator.configureNumericTextField(testField, TextFieldFormatConfigurator.intFormat));
    }

    @Test
    public void textFieldShouldBeConfigurateToDoubleFormatOnly(){
        TextField testField = new TextField();
        TextFieldFormatConfigurator configurator = new TextFieldFormatConfigurator();
        Assert.assertTrue(configurator.configureNumericTextField(testField, TextFieldFormatConfigurator.intFormat));
    }

    @Test
    public void textFieldShouldBeConfigurateCharactersOnly(){
        TextField testField = new TextField();
        TextFieldFormatConfigurator configurator = new TextFieldFormatConfigurator();
        Assert.assertTrue(configurator.configureCharacterTextField(testField));
    }

}
