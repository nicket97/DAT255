import os
import glob
# Go into the image directory
os.chdir("images")

# Create a list with all files that contain .txt in /images/
# Make another list that contains every element except the last one
files = glob.glob('*.txt')
files = files[:-1]

# Remove all files in the other list
for f in files:
    os.remove(f)
