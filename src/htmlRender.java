import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Stack;

/**
 * Created by devon on 11/16/15.
 */
public class htmlRender {


    public static void html() throws Exception {
        URL oracle = new URL("http://www.siu.edu");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(oracle.openStream()));

        Stack<String> s = new Stack<String>();

        String closingTag;
        String openingTag;
        String inputLine;
        boolean spaceReached;
        boolean isClosed;
        boolean selfClosing;
        char[] inputArray;
        String[] selfClosingTagArray = {"<area>", "<base>", "<br>", "<br/>", "<col>",
                "<embed>", "<hr>", "<hr/>", "<img>", "<input>", "<keygen>", "<link>",
                "<menuitem>", "<meta>", "<param>", "<source>", "<track>", "<wbr>"};
        /*
         Self closing tags aka void elements taken from W3 Schools:
          >  http://www.w3.org/html/wg/drafts/html/master/syntax.html#void-elements
        */


        int counter = 0;
        PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
        while ((inputLine = in.readLine()) != null) { // Go through each line
            writer.println(inputLine);

            counter++;
            closingTag = "";
            openingTag = "";

            inputArray = inputLine.toCharArray();
            // I found it easier to iterate through an array of characters to look for tags.

            for (int i = 0; i < inputArray.length; i++) {

                if (inputArray[i] == '<') {
                    if (inputArray[i + 1] == '/') {
                        openingTag = s.pop();
                        // pop off the opening tag for comparrison
                        closingTag += "<";
                        // closing tag is stored without the '/' so it can be easily compared to the opening.

                        for (int j = i + 2; j < inputArray.length; j++) {
                            // Since the first two characters, "</", are accounted for, we start at the third
                            closingTag += inputArray[j];

                            if (inputArray[j] == '>') {
                                break;
                            } //end if

                        } // end for


                        // Check the last opening tag after a closing tag has been found
                        // This section still needs some work
                        if (closingTag.equals(openingTag)) {
                            //System.out.println(openingTag + " -- " + closingTag);
                            System.out.println("------SUCCESS-----");
                            System.out.println(openingTag + " -- " + closingTag);
                            System.out.println("-------------------\n");
                            closingTag = "";
                            openingTag = "";
                            // Reset the tags to allow for mutiple tags per row
                        } // end if
                        else {
                            System.out.println("\nError Line " + counter + "....");
                            // I don't think the line counter works...
                            System.out.println("-------------------");
                            System.out.println(openingTag + " -- " + closingTag);
                            System.out.println("-------------------\n");
                        } // end else

                    } //end if


                    else {
                        // else, it must be an opening tag.

                        openingTag += "<";
                        spaceReached = false;
                        isClosed = false;
                        // spaceReached is used to find the end of the tag name and the start of
                        // things like id, name, etc. We don't want to store these things, so
                        // we can just skip over them.

                        for (int j = i + 1; j < inputArray.length; j++) {
                            // Since "<" has already been acounted for, we don't can start at i+1

                            if (inputArray[j] == ' ') {
                                //check for the spacereached

                                spaceReached = true;
                            } //end if

                            if (inputArray[j] == '>' && openingTag.charAt(1) != '!') {
                                // This finds the end of the tag, and only continues it if it is
                                // not a comment.

                                openingTag += inputArray[j];

                                selfClosing = false;

                                for (String tag : selfClosingTagArray) {
                                    // Check if the opening tag is a self-closing tag
                                    if (openingTag.equals(tag)) {
                                        selfClosing = true;
                                    } // end if
                                } // end for

                                if (!selfClosing) {
                                    // Validate that the tag is not self closing before pushing
                                    s.push(openingTag);
                                    System.out.println("Opening: " + openingTag);
                                    isClosed = true;
                                    //System.out.println(openingTag);
                                    // Reset the tags to allow for mutiple tags per row
                                } //end if
                                openingTag = "";
                                closingTag = "";


                                break;
                            } //end if

                            else if (!spaceReached && !isClosed) {
                                // If it's not the end of the tag and a space has not been found,
                                // the string is updated with the new character

                                openingTag += inputArray[j];
                            }  //end if
                        } // end for
                    } //end else
                } // end if

            } //end for

        } //end while

        writer.close();
    }
}