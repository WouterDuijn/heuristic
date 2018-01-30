setwd("C:/workspace/heuristic/doc")
source("data.r")

#Hillclimber, Hillclimber restart
#H0: hillclimber = hillclimberrestart
#H1: hillclimber > hillclimberrestart
wilcox.test(hillclimber, hillclimberrestart, alternative="greater") #p-value=(almost)1 
# not very useful test

wilcox.test(hillclimber, simulated, alternative="greater") #p-value=(almost)1 
# not very useful test

#H1: hillclimberrestart > hillclimber
# reject H0, H1 likely
wilcox.test(hillclimberrestart, hillclimber, alternative="greater") #p-value=2.938e-14

#H1: hillclimberrestart > simulated
# reject H0, H1 likely
wilcox.test(hillclimberrestart, simulated, alternative="greater") #p-value=0.0005227

#H1: simulated > hillclimber
# reject H0, H1 likely
wilcox.test(simulated, hillclimber, alternative="greater") #p-value=6.743e-11

#H1: simulated > hillclimberrestart
#H0 not rejected, distributions can be the same/equal.
wilcox.test(simulated, hillclimberrestart, alternative="greater") #p-value=0.9995
