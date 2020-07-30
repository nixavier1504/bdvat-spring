import sys
import os
import json
import glob
import pandas as pd
import numpy as np
#import statsmodels.api as sm
import statsmodels.regression.linear_model as sm


def desc_stats(fname,column_names,sqlContext):
  
  
  df_parquet =  sqlContext.read.parquet(fname)
  df=df_parquet.toPandas()
  df=df[column_names]
  desc = df.describe()
  json_desc = desc.to_json(orient='columns')
  return(json_desc)
  
  
def corr_coeff(fname,column_names,sqlContext):
  
  df_parquet =  sqlContext.read.parquet(fname)
  df=df_parquet.toPandas()
  df=df[column_names]
  df_corr = df.corr()
  json_corr = df_corr.to_json(orient='columns')
  return(json_corr)
  
  
def lm_model(fname , column_names , vp ,sqlContext ):
	
  val_percent = vp
  df_parquet =  sqlContext.read.parquet(fname)
  df=df_parquet.toPandas()
  df=df[column_names]
  df=df.dropna()
  df = df.select_dtypes(include=[np.number])
  models=[]
  df_resp=pd.DataFrame([])
  ind_col = ''
  for resp in df.columns:
    ind_col = ''
    for col in df.loc[:, df.columns != resp].columns:
		ind_col += col
		ind_col += '+'
		
    ind_col = ind_col[:-1]
    formula = resp + "~" + ind_col
    
    models.append(sm.OLS.from_formula(formula, data = df).fit())

  for x in range(len(df.columns)):
    df_resp=df_resp.append(models[x].summary2().tables[0])
  
  return(df_resp.reset_index().to_json(orient='table'))
  
if (__name__ == '__main__'):
    
    if len(sys.argv) > 1:
        desc_stats(sys.argv[1])
