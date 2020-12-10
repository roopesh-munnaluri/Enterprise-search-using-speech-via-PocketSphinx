names=open("transcripts.txt",'r')
updatedNames=open("name2.txt",'a')
for name in names:
  updatedNames.write('<s> ' + name.rstrip() + ' </s>' + '\n')
updatedNames.close()
