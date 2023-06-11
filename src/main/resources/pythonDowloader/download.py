from pytube import YouTube
import sys
import os

id = str(sys.argv[2])
link = str(sys.argv[1])

print("link:  " + link)
print("id:  " + id)
yt = YouTube(link)
stream = yt.streams.filter(only_audio=True).first()
print("Downloading.. ")
stream.download()
out_file = stream.download(filename=id+".mp3", output_path="./sounds")
os.system("rm -f *.mp4")
print("Downloaded.. ")
