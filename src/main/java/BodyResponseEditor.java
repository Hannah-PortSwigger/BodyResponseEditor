import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;

public class BodyResponseEditor implements BurpExtension
{
    @Override
    public void initialize(MontoyaApi api)
    {
        api.extension().setName("Display body in response editor");

        api.userInterface().registerHttpResponseEditorProvider(creationContext -> new MyExtensionProvidedHttpResponseEditor(api));
    }
}
