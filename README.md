# Support and updates for this library ended.

# command-processor
Used to work better with commands

## Build

To build this project you must clone it and add your Spigot **as SpigotServer.jar** in Libs Folder.

After this start **gradlew jar** or **gradlew publishToMavenLocal**

## Usage

CoreCommand Class is used for this Module. You need this to process Command Annotations. See example in **SoundCommand.java**.
In the Engine Class there is the Parser Instance. 

The parser is used to parse String parameter to other Parameters for the Command Method.
Default Parser like StringParser and IntegerParser are already existing. Enums are also parsed already.
The register new Parser create class with Abstract Class CommandParameterParser and add it to your Parser Instance.

In the **SoundCommand** example, the @Default Annotation is called when you use only /sound. All other @Sub Annotations are used for Sub Commands. See all Sub Commands with
/sound

If you want to have some command like /broadcast <Message> use {message} at the end of the @Sub Value. Then the processor parse all Command Arguments starting at this length as part of the String. See example at **SoundCommand** @Sub("list add {message}")
