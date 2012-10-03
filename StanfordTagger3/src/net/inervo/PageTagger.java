package net.inervo;

import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class PageTagger {
	// General notes:
	//
	// * This is excessively commented to give an idea of why I made certain decisions.
	// * One of those decisions was to not handle exceptions. I'm throwing them up the stack.

	protected MaxentTagger mt;

	// part 1, step 2:
	//
	// Write a class called PageTagger that internally contains an instance of
	// edu.stanford.nlp.tagger.maxent.MaxentTagger initialized with a english-left3words-distsim.tagger file
	// that comes with the tagger library.
	//
	// Yes, I'm using a hardcoded path. I feel a sense of shame.
	PageTagger() throws IOException, ClassNotFoundException {
		mt = new MaxentTagger("/home/tedder/Downloads/stanford-postagger-2012-07-09/models/wsj-0-18-left3words.tagger");
	}

	// part 1, step 3:
	//
	// Create a method on this class called tagText that takes a String as input and returns the tagged text,
	// another String, as output. Internally the method should use the tagString method of MaxentTagger to tag
	// the String.
	public String tagText(String inputText) {
		String ret = mt.tagString(inputText);

		return ret;
	}

	// part 1, step 4:
	//
	// Create another method called getText that takes a java.net.URL as input and returns a String containing
	// all the text in the body of a web page excluding HTML tags and JavaScript. For example, if the page has
	// (truncated) the method should return hello world. It is okay to use a 3rd party HTML parsing library if
	// you prefer.
	public String getText(URL url) throws IOException {
		// user-agent method found here: http://stackoverflow.com/a/7146644/659298
		// if needed, here's a generic user agent:
		// Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6
		//
		// note Jsoup.connect takes a string representation of the URL, not the URL representation of it. It's
		// in somewhat bad form to use .toString() for non-debugging purposes, which is why .toExternalForm()
		// exists.
		return Jsoup.connect(url.toExternalForm()).userAgent("tedderbot/0.1").get().text();
	}

	// helper method for 4a that allows us to test the tagger without external dependencies.
	public String getText(String html) {
		return Jsoup.parse(html).text();
	}

	// part 1, step 5:
	//
	// Write a static main method that allows you to pass in an arbirary[sic] web page URL, uses the getText
	// and tagText methods to return a tagged String.
	//
	// "to return a tagged String" implied this isn't the default main method. I've implemented both for
	// convenience.
	public static String main(URL url) throws Exception {
		PageTagger pt = new PageTagger();
		// there's a lot that can go wrong on the following line. An argument could be made to spread it out.
		return pt.tagText(pt.getText(url));

	}

	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			System.out.println("Please specify URL as the one and only argument.");
		} else {
			main(new URL(args[0]));
		}

	}

}
