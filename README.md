# Errgent - Grammatical Error Generation Toolkit 🤖

![maven](https://github.com/manzurola/errgent/actions/workflows/maven.yml/badge.svg)

Generate grammatical mistakes in natural language text.

Article - [Errgent - Grammatical Error Generator](https://guymanzurola.medium.com/errgent-grammatical-error-generation-toolkit-8048cf9d03ae).

## Prerequisits

Before you begin, ensure you have met the following requirements:

* You have Java 11 installed.
* You have the necessary prerequisites for [Errant4J](https://github.com/manzurola/errant4j#prerequisits).

## Installing Errgent

Add this to the dependencies section of your `pom.xml`:
```xml
<dependency>
  <groupId>io.github.manzurola</groupId>
  <artifactId>errgent</artifactId>
  <version>0.3.0</version>
</dependency>
```

## Using Errgent

To use Errgent in code, follow these steps:

```java
// Create a spacy instance (from spaCy4j)
SpaCy spacy = SpaCy.create(CoreNLPAdapter.forEnglish());

// Instantiate a new Errgent for English
Generator errgent = Errgent.forEnglish(spacy);

// Generate a specific grammatical error in the target doc. Since a
// sentence can contain multiple errors at once, all such possible
// errors are returned.
List<GeneratedError> generatedErrors = errgent.generateErrors(
    "If I were you, I would go home.",
    GrammaticalError.REPLACEMENT_SUBJECT_VERB_AGREEMENT
);

// Print out the results. The markedText() method retrieves the
// erroneous text with the error marked by an asterisk on both sides.
// We can also access the char offsets of the error using charStart
// and charEnd methods of GeneratedError.
for (GeneratedError generatedError : generatedErrors) {
    String text = generatedError.markedText();
    System.out.printf(
        "%s, %s%n",
        text,
        generatedError.error()
    );
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
