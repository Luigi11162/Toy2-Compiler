SET toy2Path=..\source_files\TestModifica\Valid1.toy2
SET compilerPath=jar_file
SET c_outPath=source_files//generated_c


cd %compilerPath%

SET jar_file=toy2Compiler-jar-with-dependencies.jar

java -jar %jar_file% %toy2Path%

cd %c_outPath%

gcc out_c.c

a.exe