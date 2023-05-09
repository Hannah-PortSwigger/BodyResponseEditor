import burp.api.montoya.MontoyaApi;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.responses.HttpResponse;
import burp.api.montoya.ui.Selection;
import burp.api.montoya.ui.editor.EditorOptions;
import burp.api.montoya.ui.editor.RawEditor;
import burp.api.montoya.ui.editor.extension.ExtensionProvidedHttpResponseEditor;

import java.awt.*;

import static burp.api.montoya.core.ByteArray.byteArray;

public class MyExtensionProvidedHttpResponseEditor implements ExtensionProvidedHttpResponseEditor
{
    private final RawEditor responseEditor;
    HttpRequestResponse requestResponse;

    public MyExtensionProvidedHttpResponseEditor(MontoyaApi api)
    {
        responseEditor = api.userInterface().createRawEditor(EditorOptions.READ_ONLY);
    }

    @Override
    public HttpResponse getResponse()
    {
        return requestResponse.response();
    }

    @Override
    public void setRequestResponse(HttpRequestResponse requestResponse)
    {
        this.requestResponse = requestResponse;

        HttpResponse response = requestResponse.response();

//        Bug found using this method. If you wanted to include headers in the displayed response, then this would be a good method to use.
//        HttpResponse strippedResponse = httpResponse()
//                .withHttpVersion(response.httpVersion())
//                .withStatusCode(response.statusCode())
//                .withReasonPhrase(response.reasonPhrase())
//                .withBody(response.body());
//
//        this.responseEditor.setContents(strippedResponse.toByteArray());

        String str = response.httpVersion() + " " + response.statusCode() + " " + response.reasonPhrase() + "\r\n\r\n" + response.bodyToString();
        this.responseEditor.setContents(byteArray(str));
    }

    @Override
    public boolean isEnabledFor(HttpRequestResponse requestResponse)
    {
        return true;
    }

    @Override
    public String caption()
    {
        return "Only body";
    }

    @Override
    public Component uiComponent()
    {
        return responseEditor.uiComponent();
    }

    @Override
    public Selection selectedData()
    {
        return responseEditor.selection().isPresent() ? responseEditor.selection().get() : null;
    }

    @Override
    public boolean isModified()
    {
        return responseEditor.isModified();
    }
}
