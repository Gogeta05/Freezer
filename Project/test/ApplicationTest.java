import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;

import java.util.ArrayList;

import models.User;

import org.fest.assertions.AssertExtension;
import org.junit.Test;

import play.twirl.api.Content;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationTest {

    @Test
    public void simpleCheck() {
        int a = 1 + 1;
        assertThat(a).isEqualTo(2);
    }

    @Test
    public void renderTemplate() {
        //Content html = views.html.index.render("Your new application is ready.");
    	Content html = views.html.index.render(new ArrayList<User>());
        assertThat(contentType(html)).isEqualTo("text/html");
       // assertThat(contentAsString(html)).contains("Your new application is ready.");
    }


}
