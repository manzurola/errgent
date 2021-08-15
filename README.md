# Errgent - Grammatical Error Generation Toolkit 🤖

Errgent generates grammatically incorrect variances of a valid English sentence.

## How It Works

We first create variances of the document by inflecting each token in the document. For English, we use SimpleNLG to create inflections in a brute force manner. 

We then process each inflected doc by Errant, which annotates each doc pair (inflected vs original) for grammatical errors.

Finally, we filter each pair to match those which contain the specified grammatical error. 

## Prerequisits

Before you begin, ensure you have met the following requirements:

* You have Java 11 installed.
* You have access to Github Packages Maven registry as described [here](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-apache-maven-registry#authenticating-to-github-packages).
* You have the necessary prerequisites for [Errant4J](https://github.com/manzurola/errant4j#prerequisits).

## Installing Errgent

Add this to the dependencies section of your `pom.xml`:
```xml
<dependency>
  <groupId>com.github.manzurola</groupId>
  <artifactId>errgent</artifactId>
  <version>0.1.0</version>
</dependency>
```

## Using Errgent

To use Errgent in code, follow these steps:

```java
// Get a spaCy instance
SpaCy spacy = SpaCy.create(CoreNLPAdapter.create());
// Create an English error annotator
Annotator annotator = Errant.newAnnotator("en", spacy);
// Create an English error generator
Generator generator = Errgent.newGenerator("en", annotator);

Doc target = generator.parse("My friends like to have fun.");

// Generate all documents that contain the specified error 
// (will contain "My friends like to has fun.")
List<Doc> inflections = generator.generate(target, REPLACEMENT_SUBJECT_VERB_AGREEMENT);
for (Doc inflection : inflections) {
    System.out.println(inflection.text());
}
```

Errgent is currently available only for English.

## Contributions

To contribute to Errgent, follow these steps:

1. Fork this repository.
2. Create a branch: `git checkout -b <branch_name>`.
3. Make your changes and commit them: `git commit -m '<commit_message>'`
4. Push to the original branch: `git push origin <project_name>/<location>`
5. Create the pull request.

Alternatively see the GitHub documentation on [creating a pull request](https://docs.github.com/en/github/collaborating-with-pull-requests/proposing-changes-to-your-work-with-pull-requests/creating-a-pull-request).

        
## Contributors
        
Thanks to the following people who have contributed to this project:
        
* [@manzurola](https://github.com/manzurola) 🐈        

## Contact

If you want to contact me you can reach me at [guy.manzurola@gmail.com](guy.manzurola@gmail.com).

## License
        
This project uses the following license: [MIT](https://github.com/manzurola/errgent/blob/main/LICENSE).
