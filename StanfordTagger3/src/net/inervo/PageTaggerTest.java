package net.inervo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URL;

import org.junit.Test;

public class PageTaggerTest {

	@Test
	public void testComplete() {
		testSingle("http://gumgum.com/");
		testSingle("http://www.popcrunch.com/jimmy-kimmel-engaged/");
		// testSingle("http://ebeautifulworld.blogspot.com/2012/01/trace-mobile-numbers-in-india-mobile_4865.html");
		testSingle("http://www.windingroad.com/articles/reviews/quick-drive-2012-bmw-z4-sdrive28i/");
	}

	// helper method
	private void testSingle(String url) {
		try {
			System.out.println("\nurl: " + url);
			String tagged = PageTagger.main(new URL(url));
			System.out.println("tagged: " + tagged);
			assertTrue(tagged.length() > 10);
		} catch (Exception ex) {
			fail("parsing url failed: " + url);
		}
	}

	@Test
	public void testStaticHTMLtoText() throws Exception {
		String html = "<html><head><title>Simple Page</title></head><body><script>var a = 1; var b=2;</script><p>hello</p><p>world</p></body></html>";
		PageTagger pt = new PageTagger();
		String text = pt.getText(html);
		assertEquals(text, "Simple Page hello world");
	}

	@Test
	public void testTextTagging() throws Exception {
		String text = "Sorry, my 19mm hex lives in Escondido now.";
		PageTagger pt = new PageTagger();
		String tagged = pt.tagText(text);
		assertEquals(tagged, "Sorry_JJ ,_, my_PRP$ 19mm_CD hex_NN lives_NNS in_IN Escondido_NNP now_RB ._. ");
	}
}
