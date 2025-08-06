## Spring AI Demo Application
This is an example of Spring Boot Application integrated with Spring AI - OpenAI.

### Adding OpenAI token
For this application to work, you will need to provide your own OpenAI API key which is defined in `src\main\resources\application.properties`:

```
spring.ai.openai.api-key=<YOUR_API_KEY>
```

To obtain the API Key, you will first need to [create OpenAI account](https://platform.openai.com/signup). After that, go to [API Keys](https://platform.openai.com/api-keys) page and generate a new key.

### Running the application
To run the app first built it using maven:

```
mvn clean install
```

Then you can run `SpringAiDemoApplication` as regular Spring Boot app.

### Accessing REST endpoints
When the application starts, it can be interacted with using REST endpoints defined in `AiController`. the app starts at `localhost:8080`

#### Simple text prompts
To ask questions you can call `ask-ai` providing your prompt, such as:

```
http://localhost:8080/ask-ai?prompt='what is AI?'
```

#### Templated prompts
Endpoint `city-guide` uses parametrized predefined query, where placeholders are replaced with user provided parameters.
Based on city and interest provided, you will get tips what to do in that city as a tourist based on your interest.

```
http://localhost:8080/city-guide?city=Toronto&interest=history
```

#### Generating images
Image generation can be accessed via `http://localhost:8080/generate-image?prompt=xxx`, where you can provide prompt defining what image should be generated.

For example:

```
http://localhost:8080/generate-image?prompt='AI Icon'
```