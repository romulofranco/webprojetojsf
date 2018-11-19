package br.com.ifsul.fsi.chatbot;

import com.ibm.watson.developer_cloud.discovery.v1.Discovery;
import com.ibm.watson.developer_cloud.discovery.v1.model.QueryOptions;
import com.ibm.watson.developer_cloud.discovery.v1.model.QueryResponse;
import com.ibm.watson.developer_cloud.discovery.v1.model.QueryResult;
import com.ibm.watson.developer_cloud.language_translator.v3.model.TranslateOptions;
import com.ibm.watson.developer_cloud.language_translator.v3.LanguageTranslator;
import com.ibm.watson.developer_cloud.language_translator.v3.model.TranslationResult;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalysisResults;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalyzeOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.CategoriesOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.ConceptsOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.ConceptsResult;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.EmotionOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.EntitiesOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.Features;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.KeywordsOptions;
import com.ibm.watson.developer_cloud.personality_insights.v3.PersonalityInsights;
import com.ibm.watson.developer_cloud.personality_insights.v3.model.Profile;
import com.ibm.watson.developer_cloud.personality_insights.v3.model.ProfileOptions;
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneOptions;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneScore;
import java.util.Arrays;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author admin
 */
public class WatsonHelper {

    private final static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(WatsonHelper.class.getName());

    public WatsonHelper() {
    }

    public String getPersonalityInsights(String text) {
        PersonalityInsights service = new PersonalityInsights("2017-10-13");
        service.setUsernameAndPassword(Params.PI_USR, Params.PI_PWD);

        ProfileOptions options = new ProfileOptions.Builder()
                .text(text)
                .consumptionPreferences(true)
                .rawScores(true)
                .acceptLanguage("pt-br")
                .build();

        Profile profile = service.profile(options).execute();
        logger.info(profile.toString());
        return Arrays.toString(profile.getValues().toArray());
    }

    public String getNLUConcepts(String text) {
        NaturalLanguageUnderstanding service = new NaturalLanguageUnderstanding("2018-03-16");
        service.setEndPoint(Params.NLU_URL);
        service.setUsernameAndPassword(Params.NLU_USR, Params.NLU_PWD);
       EntitiesOptions entities = new EntitiesOptions.Builder()
                .sentiment(true)
                .emotion(true)
                .mentions(true)
                .limit(5)
                .build();

        EmotionOptions emotion = new EmotionOptions.Builder().build();

        CategoriesOptions categories = new CategoriesOptions();

        KeywordsOptions keywords = new KeywordsOptions.Builder()
                .sentiment(true)
                .build();

        ConceptsOptions concepts = new ConceptsOptions.Builder()
                .limit(5)
                .build();

        Features features = new Features.Builder()
                .keywords(keywords)
                .emotion(emotion)
                .entities(entities)
                .concepts(concepts)
                .categories(categories)
                .build();

        AnalyzeOptions parameters = new AnalyzeOptions.Builder()
                .html(text)
                .returnAnalyzedText(true)
                .features(features)
                .language("pt-br")
                .build();

        AnalysisResults results = service.analyze(parameters).execute();
        
        StringBuilder query = new StringBuilder();
        for (ConceptsResult keyword : results.getConcepts()) {
            query.append("'").append(keyword.getText()).append("' ");            
        }
        
        logger.info(query.toString());
        return query.toString();
    }

    public String getNLUAnalysis(String text) {
        NaturalLanguageUnderstanding service = new NaturalLanguageUnderstanding("2018-03-16");
        service.setEndPoint(Params.NLU_URL);
        service.setUsernameAndPassword(Params.NLU_USR, Params.NLU_PWD);
        EntitiesOptions entities = new EntitiesOptions.Builder()
                .sentiment(true)
                .emotion(true)
                .mentions(true)
                .limit(5)
                .build();

        EmotionOptions emotion = new EmotionOptions.Builder().build();

        CategoriesOptions categories = new CategoriesOptions();

        KeywordsOptions keywords = new KeywordsOptions.Builder()
                .sentiment(true)
                .build();

        ConceptsOptions concepts = new ConceptsOptions.Builder()
                .limit(5)
                .build();

        Features features = new Features.Builder()
                .keywords(keywords)
                .emotion(emotion)
                .entities(entities)
                .concepts(concepts)
                .categories(categories)
                .build();

        AnalyzeOptions parameters = new AnalyzeOptions.Builder()
                .html(text)
                .returnAnalyzedText(true)
                .features(features)
                .language("pt-br")
                .build();

        AnalysisResults results = service.analyze(parameters).execute();
        logger.info(results.toString());
        
        return results.getEmotion().getDocument().toString();
    }

    public List<ToneScore> getToneAnalyzer(String text) {
        ToneAnalyzer service = new ToneAnalyzer("2017-09-21");
        service.setUsernameAndPassword(Params.TONEANALYZER_USR, Params.TONEANALYZER_PWD);

        ToneOptions toneOptions = new ToneOptions.Builder()
                .html(text)
                .sentences(true)
                .build();

        ToneAnalysis tone = service.tone(toneOptions).execute();
        System.out.println(tone);
        return tone.getDocumentTone().getTones();        
    }

    public String getTranslation(String text, String source, String dest) {
        IamOptions options = new IamOptions.Builder()
                .apiKey(Params.LT_KEY)
                .build();

        LanguageTranslator languageTranslator = new LanguageTranslator("2018-05-01", options);
        languageTranslator.setEndPoint(Params.LT_URL);

        TranslateOptions translateOptions = new TranslateOptions.Builder()
                .addText(text)
                .source(source)
                .target(dest)
                .build();
        TranslationResult translationResult = languageTranslator.translate(translateOptions).execute();
        logger.info(translationResult.getTranslations().get(0).getTranslationOutput());
        return translationResult.getTranslations().get(0).getTranslationOutput();
    }

    public String getDiscovery(String text) {
        Discovery discovery = new Discovery("2018-05-23");
        discovery.setUsernameAndPassword(Params.DISCOVERY_USR, Params.DISCOVERY_PWD);
        discovery.setEndPoint(Params.DISCOVERY_URL);

        String environmentName = "system";
        String environmentDesc = "Watson News Discovery";
        String collectionId = "news-en";

        String query = this.getNLUConcepts(text);

        QueryOptions queryOptions = new QueryOptions.Builder()
                .environmentId(environmentName)
                .collectionId(collectionId)
                .count(10)
                .filter(query)
                .build();

        QueryResponse queryResponse = discovery.query(queryOptions).execute();
        List<QueryResult> results = queryResponse.getResults();
        return Arrays.toString(results.toArray());
    }
}
