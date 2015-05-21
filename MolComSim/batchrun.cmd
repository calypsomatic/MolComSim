FOR /L %%j IN (1,1,10) DO (
FOR /L %%i IN (1,1,10) DO (
	start /B java -jar dist\lib\MolComSim.jar -batchRun
)
timeout /t 6 /nobreak
)

