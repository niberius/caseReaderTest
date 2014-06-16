import a1qa.utils.CaseReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class CaseReaderTest {

    private CaseReader caseReader = new CaseReader();

    @Test
    public void invalidPathTest(){
        try{
            String path = caseReader.selectCases(new File("asasda"), 10);
            System.out.println(path);
            Assert.assertTrue(path.equals("file not found"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void validTest() {
        String path = caseReader.selectCases(new File("./src/test/resources/all_pairs.txt"), 10);
        Assert.assertTrue(new File(path).exists());
    }

    @Test
    public void testValidateOk() {
        //TODO: количество значений в строках не соответствует количеству колонок
    }

    @Test
    public void testMaxLineSize() {
        //TODO: количество ункальных строк < запрашиваемого в результате
    }
}
