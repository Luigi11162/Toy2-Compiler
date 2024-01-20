SET toy2Path=source_files\ProgramEs5.inp
SET compilerPath=target//classes//esercitazione5//Test//
SET classPath=%~dp0src\esercitazione5

cd %compilerPath%

java TestCodeVisitor %toy2Path%
