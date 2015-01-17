package com.kangaroo.lucen;

import com.kangaroo.model.Contact;
import com.kangaroo.utility.Constants;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;

/**
 * @author Hikamt Dhamee
 * @email me.hemant.available@gmail.com
 */
public class Index {
    public void indexContact(Contact contact) throws IOException{
        System.out.println("Indexing to directory '" + Constants.INDEX_NAME + "'...");

        Directory dir = FSDirectory.open(new File(Constants.INDEX_NAME));
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);
        IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_40, analyzer);

        // Add new documents to an existing index:
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        IndexWriter writer = new IndexWriter(dir, iwc);
        indexDocs(writer, contact);

        writer.commit();
        writer.close();

    }
    public void indexDocs(IndexWriter writer,Contact contact) throws IOException{
        Document document = new Document();
        document.add(new TextField("contactName",contact.getContactName(), Field.Store.YES));
        document.add(new TextField("contactNumber",contact.getContactNumber(), Field.Store.YES));

        // New index, so we just add the document (no old document can be there):
        System.out.println("adding " + document.toString());
        writer.addDocument(document);

    }

    public  void searchContact(Contact contact) throws IOException, ParseException {
        Directory dir = FSDirectory.open(new File(Constants.INDEX_NAME));
        // Now search the index:
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher isearcher = new IndexSearcher( reader); // read-only=true
        // Parse a simple query that searches for "text
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);
        QueryParser parser = new QueryParser(Version.LUCENE_40, "contactName", analyzer);
        Query query = parser.parse("contactName");
        ScoreDoc[] hits = isearcher.search(query, null, 1000).scoreDocs;
        // Iterate through the results:
        for (ScoreDoc hit : hits) {
            Document hitDoc = isearcher.doc(hit.doc);
            System.out.println(">>>>>>>>>" + hitDoc.toString());
        }
        reader.close();
    }
}

