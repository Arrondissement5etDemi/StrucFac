#!/bin/bash
# parallel job using 16 processors. and runs for 8 hours (max)
#SBATCH -N 1 # node count
#SBATCH --ntasks-per-node=8
#SBATCH -t 8:00:00

# sends mail when process begins, and
# when it ends. Make sure you define your email
#SBATCH --mail-type=begin
#SBATCH --mail-type=end
#SBATCH --mail-user=hainaw@princeton.edu

# Load openmpi environment
module load openmpi

srun cd /home/hainaw/StrucFac
javac SkHardSphere.java
java SkHardSphere
